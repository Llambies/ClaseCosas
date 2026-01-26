import express from 'express';
import {
  createLeague,
  getLeagues,
  getLeagueById,
  joinLeague,
  updateLeague,
  deleteLeague
} from '../controllers/league.controller.js';
import { authenticate } from '../middleware/auth.middleware.js';

const router = express.Router();

router.use(authenticate); // Todas las rutas requieren autenticación

router.post('/', createLeague);
router.get('/', getLeagues);
router.get('/:id', getLeagueById);
router.post('/join', joinLeague);
router.put('/:id', updateLeague);
router.delete('/:id', deleteLeague);

export default router;
