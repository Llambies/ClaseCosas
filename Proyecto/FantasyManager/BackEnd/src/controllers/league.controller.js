import League from '../models/league.model.js';
import Team from '../models/team.model.js';
import Creature from '../models/creature.model.js';

export const createLeague = async (req, res) => {
  try {
    const { name, description, isPublic, maxParticipants, rules, availableCreatures, newCreatures } = req.body;
    
    let creatureIds = [];
    
    // Si se proporcionan nuevas criaturas, crearlas primero
    if (newCreatures && newCreatures.length > 0) {
      const createdCreatures = await Promise.all(
        newCreatures.map(async (creatureData) => {
          const creature = new Creature(creatureData);
          await creature.save();
          return creature._id;
        })
      );
      creatureIds.push(...createdCreatures);
    }
    
    // Si se proporcionan IDs de criaturas existentes, validarlas
    if (availableCreatures && availableCreatures.length > 0) {
      const creatures = await Creature.find({ _id: { $in: availableCreatures } });
      if (creatures.length !== availableCreatures.length) {
        return res.status(400).json({ error: 'Una o más criaturas no existen' });
      }
      creatureIds.push(...availableCreatures);
    }
    
    if (creatureIds.length === 0) {
      return res.status(400).json({ error: 'Debes proporcionar al menos una criatura' });
    }
    
    const league = new League({
      name,
      description,
      creator: req.user._id,
      isPublic: isPublic !== false,
      maxParticipants: maxParticipants || 20,
      rules: rules || { maxCreaturesPerTeam: 6, allowDuplicateCreatures: false },
      participants: [req.user._id],
      availableCreatures: creatureIds
    });

    await league.save();
    
    // Crear equipo inicial para el creador
    const team = new Team({
      user: req.user._id,
      league: league._id,
      name: `${req.user.username}'s Team`,
      creatures: []
    });
    await team.save();

    // Populate para devolver datos completos
    await league.populate('creator', 'username email');
    await league.populate('participants', 'username email');
    await league.populate('availableCreatures', 'name types baseStats moves abilities basePrice sprites imageUrl');

    res.status(201).json(league);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

export const getLeagues = async (req, res) => {
  try {
    const { isPublic, search } = req.query;
    const query = {};
    
    if (isPublic === 'true') {
      query.isPublic = true;
      query.status = { $in: ['draft', 'active'] };
    }
    
    if (search) {
      query.name = { $regex: search, $options: 'i' };
    }

    const leagues = await League.find(query)
      .populate('creator', 'username email')
      .populate('participants', 'username')
      .populate('availableCreatures', 'name types basePrice imageUrl sprites')
      .sort({ createdAt: -1 });

    res.json(leagues);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const getLeagueById = async (req, res) => {
  try {
    const league = await League.findById(req.params.id)
      .populate('creator', 'username email')
      .populate('participants', 'username email budget totalPoints')
      .populate('availableCreatures', 'name types baseStats moves abilities basePrice sprites imageUrl');

    if (!league) {
      return res.status(404).json({ error: 'Liga no encontrada' });
    }

    res.json(league);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const joinLeague = async (req, res) => {
  try {
    const { code } = req.body;
    const league = await League.findOne({
      $or: [
        { _id: code },
        { invitationCode: code }
      ]
    });

    if (!league) {
      return res.status(404).json({ error: 'Liga no encontrada' });
    }

    if (league.participants.length >= league.maxParticipants) {
      return res.status(400).json({ error: 'La liga está llena' });
    }

    if (league.participants.includes(req.user._id)) {
      return res.status(400).json({ error: 'Ya estás en esta liga' });
    }

    league.participants.push(req.user._id);
    await league.save();

    // Crear equipo para el nuevo participante
    const team = new Team({
      user: req.user._id,
      league: league._id,
      name: `${req.user.username}'s Team`,
      creatures: []
    });
    await team.save();

    res.json({ message: 'Te has unido a la liga correctamente', league });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

export const updateLeague = async (req, res) => {
  try {
    const league = await League.findById(req.params.id);

    if (!league) {
      return res.status(404).json({ error: 'Liga no encontrada' });
    }

    if (league.creator.toString() !== req.user._id.toString()) {
      return res.status(403).json({ error: 'No tienes permiso para editar esta liga' });
    }

    Object.assign(league, req.body);
    await league.save();

    res.json(league);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

export const deleteLeague = async (req, res) => {
  try {
    const league = await League.findById(req.params.id);

    if (!league) {
      return res.status(404).json({ error: 'Liga no encontrada' });
    }

    if (league.creator.toString() !== req.user._id.toString()) {
      return res.status(403).json({ error: 'No tienes permiso para eliminar esta liga' });
    }

    await League.findByIdAndDelete(req.params.id);
    res.json({ message: 'Liga eliminada correctamente' });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
