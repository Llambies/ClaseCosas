import User from '../models/user.model.js';
import { generateToken } from '../utils/jwt.utils.js';

export const register = async (req, res) => {
  try {
    const { email, password, username } = req.body;

    // Validaciones
    if (!email || !password || !username) {
      return res.status(400).json({ error: 'Todos los campos son requeridos' });
    }

    // Verificar si el usuario ya existe
    const existingUser = await User.findOne({
      $or: [{ email }, { username }]
    });

    if (existingUser) {
      return res.status(400).json({ error: 'El email o username ya está en uso' });
    }

    // Crear nuevo usuario
    const user = new User({ email, password, username });
    await user.save();

    // Generar token
    const token = generateToken(user._id);

    res.status(201).json({
      message: 'Usuario registrado correctamente',
      token,
      user: {
        id: user._id,
        email: user.email,
        username: user.username,
        budget: user.budget
      }
    });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

export const login = async (req, res) => {
  try {
    const { email, password } = req.body;

    if (!email || !password) {
      return res.status(400).json({ error: 'Email y contraseña son requeridos' });
    }

    // Buscar usuario
    const user = await User.findOne({ email });
    if (!user) {
      return res.status(401).json({ error: 'Credenciales inválidas' });
    }

    // Verificar contraseña
    const isMatch = await user.comparePassword(password);
    if (!isMatch) {
      return res.status(401).json({ error: 'Credenciales inválidas' });
    }

    // Generar token
    const token = generateToken(user._id);

    res.json({
      message: 'Login exitoso',
      token,
      user: {
        id: user._id,
        email: user.email,
        username: user.username,
        budget: user.budget,
        totalPoints: user.totalPoints
      }
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const getProfile = async (req, res) => {
  try {
    const user = await User.findById(req.user._id).select('-password');
    res.json(user);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
