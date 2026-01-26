import Auction from '../models/auction.model.js';
import User from '../models/user.model.js';
import Team from '../models/team.model.js';

export const createAuction = async (req, res) => {
  try {
    const { creature, league, startingPrice, durationHours = 24 } = req.body;
    
    const startTime = new Date();
    const endTime = new Date(startTime.getTime() + durationHours * 60 * 60 * 1000);

    const auction = new Auction({
      creature,
      league,
      startingPrice,
      currentPrice: startingPrice,
      startTime,
      endTime,
      status: 'active'
    });

    await auction.save();
    await auction.populate('creature');
    
    res.status(201).json(auction);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

export const getAuctions = async (req, res) => {
  try {
    const { leagueId, status } = req.query;
    const query = {};

    if (leagueId) {
      query.league = leagueId;
    }

    if (status) {
      query.status = status;
    } else {
      query.status = { $in: ['active', 'pending'] };
    }

    const auctions = await Auction.find(query)
      .populate('creature')
      .populate('league', 'name')
      .populate('winner', 'username')
      .populate('bids.user', 'username')
      .sort({ endTime: 1 });

    res.json(auctions);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const placeBid = async (req, res) => {
  try {
    const { auctionId } = req.params;
    const { amount } = req.body;

    const auction = await Auction.findById(auctionId);
    if (!auction) {
      return res.status(404).json({ error: 'Subasta no encontrada' });
    }

    if (auction.status !== 'active') {
      return res.status(400).json({ error: 'La subasta no está activa' });
    }

    if (new Date() > auction.endTime) {
      auction.status = 'closed';
      await auction.save();
      return res.status(400).json({ error: 'La subasta ha finalizado' });
    }

    if (amount <= auction.currentPrice) {
      return res.status(400).json({ error: 'La puja debe ser mayor al precio actual' });
    }

    // Verificar presupuesto
    const user = await User.findById(req.user._id);
    if (user.budget < amount) {
      return res.status(400).json({ error: 'Presupuesto insuficiente' });
    }

    // Verificar que el usuario pertenece a la liga
    const team = await Team.findOne({ user: req.user._id, league: auction.league });
    if (!team) {
      return res.status(403).json({ error: 'No perteneces a esta liga' });
    }

    // Agregar puja
    auction.bids.push({
      user: req.user._id,
      amount,
      timestamp: new Date()
    });

    auction.currentPrice = amount;
    auction.winner = req.user._id;

    await auction.save();
    await auction.populate('creature');
    await auction.populate('bids.user', 'username');

    res.json(auction);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

export const closeAuction = async (req, res) => {
  try {
    const { auctionId } = req.params;
    const auction = await Auction.findById(auctionId);

    if (!auction) {
      return res.status(404).json({ error: 'Subasta no encontrada' });
    }

    if (auction.status === 'closed') {
      return res.json(auction);
    }

    auction.status = 'closed';

    // Si hay ganador, asignar criatura y deducir presupuesto
    if (auction.winner) {
      const winner = await User.findById(auction.winner);
      const team = await Team.findOne({ user: auction.winner, league: auction.league });

      if (winner && team) {
        // Deduct budget
        winner.budget -= auction.currentPrice;
        await winner.save();

        // Add creature to team (simplified - you might want to add validation)
        // This is a basic implementation
      }
    }

    await auction.save();
    await auction.populate('creature');
    await auction.populate('winner', 'username');

    res.json(auction);
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};
