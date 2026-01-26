import express from 'express';
import {
  getTeam,
  updateTeam,
  getLeagueTeams
} from '../controllers/team.controller.js';
import { authenticate } from '../middleware/auth.middleware.js';

const router = express.Router();

router.use(authenticate);

router.get('/league/:leagueId', getTeam);
router.put('/league/:leagueId', updateTeam);
router.get('/league/:leagueId/all', getLeagueTeams);

export default router;
