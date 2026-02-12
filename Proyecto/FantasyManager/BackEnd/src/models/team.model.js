import mongoose from 'mongoose';

const evSchema = new mongoose.Schema({
  hp: { type: Number, default: 0, min: 0, max: 252 },
  attack: { type: Number, default: 0, min: 0, max: 252 },
  defense: { type: Number, default: 0, min: 0, max: 252 },
  spAttack: { type: Number, default: 0, min: 0, max: 252 },
  spDefense: { type: Number, default: 0, min: 0, max: 252 },
  speed: { type: Number, default: 0, min: 0, max: 252 }
}, { _id: false });

const teamCreatureSchema = new mongoose.Schema({
  creature: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Creature',
    required: true
  },
  nickname: {
    type: String,
    trim: true
  },
  evs: {
    type: evSchema,
    default: () => ({})
  },
  moves: [{
    type: String,
    required: true
  }],
  nature: {
    type: String,
    default: 'neutral'
  },
  item: {
    type: String,
    default: ''
  }
}, { _id: false });

const teamSchema = new mongoose.Schema({
  user: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  league: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'League',
    required: true
  },
  name: {
    type: String,
    required: true,
    trim: true
  },
  creatures: [teamCreatureSchema],
  isActive: {
    type: Boolean,
    default: true
  }
}, {
  timestamps: true
});

// Validación: máximo 6 criaturas
teamSchema.pre('save', function(next) {
  if (this.creatures.length > 6) {
    return next(new Error('Un equipo no puede tener más de 6 criaturas'));
  }
  
  // Validar que no haya criaturas duplicadas
  const creatureIds = this.creatures.map(c => c.creature.toString());
  const uniqueIds = [...new Set(creatureIds)];
  if (creatureIds.length !== uniqueIds.length) {
    return next(new Error('No se pueden tener criaturas duplicadas en el equipo'));
  }
  
  // Validar que la suma de EVs no exceda 510
  this.creatures.forEach(creature => {
    const totalEVs = Object.values(creature.evs || {}).reduce((sum, val) => sum + val, 0);
    if (totalEVs > 510) {
      return next(new Error(`La criatura ${creature.creature} excede el límite de 510 EVs`));
    }
  });
  
  next();
});

const Team = mongoose.model('Team', teamSchema);

export default Team;
