import mongoose from 'mongoose';

const bidSchema = new mongoose.Schema({
  user: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  amount: {
    type: Number,
    required: true,
    min: 0
  },
  timestamp: {
    type: Date,
    default: Date.now
  }
}, { _id: false });

const auctionSchema = new mongoose.Schema({
  creature: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Creature',
    required: true
  },
  league: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'League',
    required: true
  },
  startingPrice: {
    type: Number,
    required: true,
    min: 0
  },
  currentPrice: {
    type: Number,
    required: true,
    min: 0
  },
  bids: [bidSchema],
  startTime: {
    type: Date,
    required: true
  },
  endTime: {
    type: Date,
    required: true
  },
  status: {
    type: String,
    enum: ['pending', 'active', 'closed', 'cancelled'],
    default: 'pending'
  },
  winner: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    default: null
  }
}, {
  timestamps: true
});

// Índice para búsquedas eficientes
auctionSchema.index({ league: 1, status: 1, endTime: 1 });

const Auction = mongoose.model('Auction', auctionSchema);

export default Auction;
