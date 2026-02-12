import express from 'express';
import cors from 'cors';
import dotenv from 'dotenv';

// Importar rutas
import authRoutes from './routes/auth.routes.js';
import leagueRoutes from './routes/league.routes.js';
import creatureRoutes from './routes/creature.routes.js';
import teamRoutes from './routes/team.routes.js';
import auctionRoutes from './routes/auction.routes.js';
import battleRoutes from './routes/battle.routes.js';

dotenv.config();

const app = express();

// Middlewares
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Rutas
app.use('/api/auth', authRoutes);
app.use('/api/leagues', leagueRoutes);
app.use('/api/creatures', creatureRoutes);
app.use('/api/teams', teamRoutes);
app.use('/api/auctions', auctionRoutes);
app.use('/api/battles', battleRoutes);

// Ruta de prueba
app.get('/', (req, res) => {
  res.json({
    message: 'API Fantasy Manager funcionando correctamente',
    version: '1.0.0'
  });
});

// Manejo de rutas no encontradas
app.use((req, res) => {
  res.status(404).json({
    error: 'Ruta no encontrada'
  });
});

// Manejo de errores
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({
    error: 'Error interno del servidor',
    message: process.env.NODE_ENV === 'development' ? err.message : undefined
  });
});

export default app;
