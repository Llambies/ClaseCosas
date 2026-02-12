import mongoose from 'mongoose';

const turnLogSchema = new mongoose.Schema({
  turn: { type: Number, required: true },
  team1Action: {
    creature: String,
    move: String,
    damage: Number,
    target: String
  },
  team2Action: {
    creature: String,
    move: String,
    damage: Number,
    target: String
  },
  statusEffects: [{
    team: Number,
    creature: String,
    effect: String
  }]
}, { _id: false });

const battleSchema = new mongoose.Schema({
  league: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'League',
    required: true
  },
  team1: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Team',
    required: true
  },
  team2: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Team',
    required: true
  },
  battleDate: {
    type: Date,
    required: true
  },
  winner: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Team',
    default: null
  },
  isDraw: {
    type: Boolean,
    default: false
  },
  turnLog: [turnLogSchema],
  team1Score: {
    damageDealt: { type: Number, default: 0 },
    knockouts: { type: Number, default: 0 },
    points: { type: Number, default: 0 }
  },
  team2Score: {
    damageDealt: { type: Number, default: 0 },
    knockouts: { type: Number, default: 0 },
    points: { type: Number, default: 0 }
  },
  status: {
    type: String,
    enum: ['pending', 'completed', 'failed'],
    default: 'pending'
  }
}, {
  timestamps: true
});

const Battle = mongoose.model('Battle', battleSchema);

export default Battle;
