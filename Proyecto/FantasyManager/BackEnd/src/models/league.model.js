import mongoose from 'mongoose';

const leagueSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
    trim: true
  },
  description: {
    type: String,
    trim: true
  },
  creator: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  isPublic: {
    type: Boolean,
    default: true
  },
  invitationCode: {
    type: String,
    unique: true,
    sparse: true
  },
  maxParticipants: {
    type: Number,
    default: 20,
    min: 2,
    max: 50
  },
  participants: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  }],
  status: {
    type: String,
    enum: ['draft', 'active', 'finished'],
    default: 'draft'
  },
  rules: {
    maxCreaturesPerTeam: {
      type: Number,
      default: 6
    },
    allowDuplicateCreatures: {
      type: Boolean,
      default: false
    }
  },
  availableCreatures: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Creature'
  }]
}, {
  timestamps: true
});

// Generar código de invitación único antes de guardar
leagueSchema.pre('save', async function(next) {
  if (!this.invitationCode && !this.isPublic) {
    this.invitationCode = Math.random().toString(36).substring(2, 10).toUpperCase();
  }
  next();
});

const League = mongoose.model('League', leagueSchema);

export default League;
