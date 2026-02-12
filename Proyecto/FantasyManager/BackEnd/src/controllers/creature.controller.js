import Creature from '../models/creature.model.js';

export const createCreature = async (req, res) => {
  try {
    const creature = new Creature(req.body);
    await creature.save();
    res.status(201).json(creature);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

export const getCreatures = async (req, res) => {
  try {
    const { search, type, isActive } = req.query;
    const query = {};

    if (search) {
      query.name = { $regex: search, $options: 'i' };
    }

    if (type) {
      query.types = type;
    }

    if (isActive !== undefined) {
      query.isActive = isActive === 'true';
    }

    const creatures = await Creature.find(query).sort({ name: 1 });
    res.json(creatures);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const getCreatureById = async (req, res) => {
  try {
    const creature = await Creature.findById(req.params.id);
    
    if (!creature) {
      return res.status(404).json({ error: 'Criatura no encontrada' });
    }

    res.json(creature);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const updateCreature = async (req, res) => {
  try {
    const creature = await Creature.findByIdAndUpdate(
      req.params.id,
      req.body,
      { new: true, runValidators: true }
    );

    if (!creature) {
      return res.status(404).json({ error: 'Criatura no encontrada' });
    }

    res.json(creature);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

export const deleteCreature = async (req, res) => {
  try {
    const creature = await Creature.findByIdAndDelete(req.params.id);

    if (!creature) {
      return res.status(404).json({ error: 'Criatura no encontrada' });
    }

    res.json({ message: 'Criatura eliminada correctamente' });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
