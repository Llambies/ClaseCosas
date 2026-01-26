import express from 'express';
import {
  createCreature,
  getCreatures,
  getCreatureById,
  updateCreature,
  deleteCreature
} from '../controllers/creature.controller.js';
import { authenticate } from '../middleware/auth.middleware.js';

const router = express.Router();

// Las rutas de lectura no requieren autenticación (pueden ser públicas)
router.get('/', getCreatures);
router.get('/:id', getCreatureById);

// Las rutas de escritura requieren autenticación
router.use(authenticate);
router.post('/', createCreature);
router.put('/:id', updateCreature);
router.delete('/:id', deleteCreature);

export default router;
