import Team from '../models/team.model.js';
import League from '../models/league.model.js';

export const getTeam = async (req, res) => {
  try {
    const { leagueId } = req.params;
    const team = await Team.findOne({
      user: req.user._id,
      league: leagueId
    }).populate('creatures.creature');

    if (!team) {
      return res.status(404).json({ error: 'Equipo no encontrado' });
    }

    res.json(team);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const updateTeam = async (req, res) => {
  try {
    const { leagueId } = req.params;
    const { name, creatures } = req.body;

    // Verificar que el usuario pertenece a la liga
    const league = await League.findById(leagueId);
    if (!league || !league.participants.includes(req.user._id)) {
      return res.status(403).json({ error: 'No perteneces a esta liga' });
    }

    let team = await Team.findOne({
      user: req.user._id,
      league: leagueId
    });

    if (!team) {
      // Crear equipo si no existe
      team = new Team({
        user: req.user._id,
        league: leagueId,
        name: name || `${req.user.username}'s Team`,
        creatures: creatures || []
      });
    } else {
      if (name) team.name = name;
      if (creatures) team.creatures = creatures;
    }

    await team.save();
    await team.populate('creatures.creature');

    res.json(team);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

export const getLeagueTeams = async (req, res) => {
  try {
    const { leagueId } = req.params;
    const teams = await Team.find({ league: leagueId })
      .populate('user', 'username email')
      .populate('creatures.creature')
      .sort({ 'user.username': 1 });

    res.json(teams);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
