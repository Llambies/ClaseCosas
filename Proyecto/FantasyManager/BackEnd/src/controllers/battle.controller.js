import Battle from '../models/battle.model.js';
import Team from '../models/team.model.js';
import League from '../models/league.model.js';
import { simulateBattle } from '../services/battle.service.js';

export const getBattles = async (req, res) => {
  try {
    const { leagueId } = req.query;
    const query = {};

    if (leagueId) {
      query.league = leagueId;
    }

    const battles = await Battle.find(query)
      .populate('team1', 'name user')
      .populate('team2', 'name user')
      .populate('winner', 'name')
      .populate('league', 'name')
      .sort({ battleDate: -1 })
      .limit(50);

    res.json(battles);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const getBattleById = async (req, res) => {
  try {
    const battle = await Battle.findById(req.params.id)
      .populate('team1', 'name user')
      .populate('team2', 'name user')
      .populate('winner', 'name')
      .populate('league', 'name');

    if (!battle) {
      return res.status(404).json({ error: 'Combate no encontrado' });
    }

    res.json(battle);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const runDailySimulation = async (req, res) => {
  try {
    const { leagueId } = req.params;
    const league = await League.findById(leagueId);

    if (!league) {
      return res.status(404).json({ error: 'Liga no encontrada' });
    }

    const teams = await Team.find({ league: leagueId });
    
    if (teams.length < 2) {
      return res.status(400).json({ error: 'Se necesitan al menos 2 equipos para simular' });
    }

    const battles = [];
    
    // Simular combates entre todos los equipos (round-robin simplificado)
    for (let i = 0; i < teams.length; i++) {
      for (let j = i + 1; j < teams.length; j++) {
        try {
          const battle = await simulateBattle(teams[i]._id, teams[j]._id, leagueId);
          battles.push(battle);
        } catch (error) {
          console.error(`Error simulando combate entre ${teams[i]._id} y ${teams[j]._id}:`, error);
        }
      }
    }

    res.json({
      message: `Simulación completada. ${battles.length} combates realizados.`,
      battles
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
