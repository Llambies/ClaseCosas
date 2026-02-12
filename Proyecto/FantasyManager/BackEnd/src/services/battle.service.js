import Battle from '../models/battle.model.js';
import Team from '../models/team.model.js';
import User from '../models/user.model.js';

// Simulación simple de combate (versión básica)
export const simulateBattle = async (team1Id, team2Id, leagueId) => {
  const team1 = await Team.findById(team1Id).populate('creatures.creature');
  const team2 = await Team.findById(team2Id).populate('creatures.creature');

  if (!team1 || !team2) {
    throw new Error('Equipos no encontrados');
  }

  const turnLog = [];
  let team1Alive = team1.creatures.length;
  let team2Alive = team2.creatures.length;
  let turn = 1;
  let team1Damage = 0;
  let team2Damage = 0;
  let team1KO = 0;
  let team2KO = 0;

  // Simulación simplificada
  while (team1Alive > 0 && team2Alive > 0 && turn <= 20) {
    const team1Creature = team1.creatures[team1.creatures.length - team1Alive];
    const team2Creature = team2.creatures[team2.creatures.length - team2Alive];

    if (!team1Creature || !team2Creature) break;

    // Calcular daño simplificado
    const team1Attack = (team1Creature.creature.baseStats.attack || 50) + 
                       (team1Creature.evs?.attack || 0) / 4;
    const team2Defense = (team2Creature.creature.baseStats.defense || 50) + 
                         (team2Creature.evs?.defense || 0) / 4;
    
    const team2Attack = (team2Creature.creature.baseStats.attack || 50) + 
                       (team2Creature.evs?.attack || 0) / 4;
    const team1Defense = (team1Creature.creature.baseStats.defense || 50) + 
                         (team1Creature.evs?.defense || 0) / 4;

    const damage1 = Math.max(1, Math.floor(team1Attack - team2Defense / 2));
    const damage2 = Math.max(1, Math.floor(team2Attack - team1Defense / 2));

    team1Damage += damage1;
    team2Damage += damage2;

    // Simular KO aleatorio (simplificado)
    if (Math.random() > 0.7) {
      team2Alive--;
      team1KO++;
    }
    if (Math.random() > 0.7 && team2Alive > 0) {
      team1Alive--;
      team2KO++;
    }

    turnLog.push({
      turn,
      team1Action: {
        creature: team1Creature.creature.name,
        move: team1Creature.moves[0] || 'Tackle',
        damage: damage1,
        target: team2Creature.creature.name
      },
      team2Action: {
        creature: team2Creature.creature.name,
        move: team2Creature.moves[0] || 'Tackle',
        damage: damage2,
        target: team1Creature.creature.name
      }
    });

    turn++;
  }

  // Determinar ganador
  let winner = null;
  let isDraw = false;

  if (team1Alive > team2Alive) {
    winner = team1Id;
  } else if (team2Alive > team1Alive) {
    winner = team2Id;
  } else {
    isDraw = true;
  }

  // Calcular puntos
  const team1Points = team1Damage + (team1KO * 100) + (winner === team1Id ? 200 : 0);
  const team2Points = team2Damage + (team2KO * 100) + (winner === team2Id ? 200 : 0);

  const battle = new Battle({
    league: leagueId,
    team1: team1Id,
    team2: team2Id,
    battleDate: new Date(),
    winner: winner,
    isDraw,
    turnLog,
    team1Score: {
      damageDealt: team1Damage,
      knockouts: team1KO,
      points: team1Points
    },
    team2Score: {
      damageDealt: team2Damage,
      knockouts: team2KO,
      points: team2Points
    },
    status: 'completed'
  });

  await battle.save();

  // Actualizar puntos de usuarios
  const user1 = await User.findById(team1.user);
  const user2 = await User.findById(team2.user);

  if (user1) {
    user1.totalPoints += team1Points;
    await user1.save();
  }

  if (user2) {
    user2.totalPoints += team2Points;
    await user2.save();
  }

  return battle;
};
