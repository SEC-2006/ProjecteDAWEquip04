-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 30-05-2025 a las 11:34:28
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ProjecteDAWEquip04`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ParaulesWordle`
--

CREATE TABLE `ParaulesWordle` (
  `id` int(5) NOT NULL,
  `paraula` varchar(5) NOT NULL,
  `idWordle` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `PartidesWordle`
--

CREATE TABLE `PartidesWordle` (
  `id` int(5) NOT NULL,
  `victoria1` int(11) NOT NULL,
  `victoria2` int(11) NOT NULL,
  `victoria3` int(11) NOT NULL,
  `victoria4` int(11) NOT NULL,
  `victoria5` int(11) NOT NULL,
  `victoria6` int(11) NOT NULL,
  `derrotes` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Pescamines`
--

CREATE TABLE `Pescamines` (
  `id` int(5) NOT NULL,
  `idPescamines` int(5) NOT NULL,
  `nivell` varchar(10) NOT NULL,
  `temps` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuaris`
--

CREATE TABLE `Usuaris` (
  `id` int(5) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `cognoms` varchar(150) DEFAULT NULL,
  `imatge` longblob DEFAULT NULL,
  `poblacio` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `contrasenya` varchar(255) DEFAULT NULL,
  `salt` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indices de la tabla `ParaulesWordle`
--
ALTER TABLE `ParaulesWordle`
  ADD PRIMARY KEY (`idWordle`);

--
-- Indices de la tabla `PartidesWordle`
--
ALTER TABLE `PartidesWordle`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `Pescamines`
--
ALTER TABLE `Pescamines`
  ADD PRIMARY KEY (`idPescamines`);

--
-- Indices de la tabla `Usuaris`
--
ALTER TABLE `Usuaris`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ParaulesWordle`
--
ALTER TABLE `ParaulesWordle`
  MODIFY `idWordle` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT de la tabla `Pescamines`
--
ALTER TABLE `Pescamines`
  MODIFY `idPescamines` int(5) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Usuaris`
--
ALTER TABLE `Usuaris`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
