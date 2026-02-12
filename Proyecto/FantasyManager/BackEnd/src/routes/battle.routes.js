import express from 'express';
import {
  getBattles,
  getBattleById,
  runDailySimulation
} from '../controllers/battle.controller.js';
import { authenticate } from '../middleware/auth.middleware.js';

const router = express.Router();

router.get('/', getBattles);
router.get('/:id', getBattleById);

router.use(authenticate);
router.post('/league/:leagueId/simulate', runDailySimulation);

export default router;
