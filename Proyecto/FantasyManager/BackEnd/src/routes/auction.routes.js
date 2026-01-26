import express from 'express';
import {
  createAuction,
  getAuctions,
  placeBid,
  closeAuction
} from '../controllers/auction.controller.js';
import { authenticate } from '../middleware/auth.middleware.js';

const router = express.Router();

router.get('/', getAuctions);
router.get('/:auctionId', getAuctions);

router.use(authenticate);
router.post('/', createAuction);
router.post('/:auctionId/bid', placeBid);
router.post('/:auctionId/close', closeAuction);

export default router;
