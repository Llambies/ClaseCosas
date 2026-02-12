import mongoose from 'mongoose';

const statSchema = new mongoose.Schema({
  hp: { type: Number, required: true, min: 1 },
  attack: { type: Number, required: true, min: 1 },
  defense: { type: Number, required: true, min: 1 },
  spAttack: { type: Number, required: true, min: 1 },
  spDefense: { type: Number, required: true, min: 1 },
  speed: { type: Number, required: true, min: 1 }
}, { _id: false });

const moveSchema = new mongoose.Schema({
  name: { type: String, required: true },
  type: { type: String, required: true },
  power: { type: Number, default: 0 },
  accuracy: { type: Number, default: 100, min: 0, max: 100 },
  pp: { type: Number, default: 5, min: 1 }
}, { _id: false });

const creatureSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
    unique: true,
    trim: true
  },
  types: [{
    type: String,
    required: true
  }],
  baseStats: {
    type: statSchema,
    required: true
  },
  moves: [moveSchema],
  abilities: [{
    type: String,
    trim: true
  }],
  basePrice: {
    type: Number,
    required: true,
    min: 0
  },
  sprites: {
    front: { type: String, default: '' },
    back: { type: String, default: '' },
    frontShiny: { type: String, default: '' },
    backShiny: { type: String, default: '' }
  },
  imageUrl: {
    type: String,
    default: ''
  },
  isActive: {
    type: Boolean,
    default: true
  }
}, {
  timestamps: true
});

const Creature = mongoose.model('Creature', creatureSchema);

export default Creature;
