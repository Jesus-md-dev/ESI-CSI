-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 25-12-2020 a las 19:38:05
-- Versión del servidor: 8.0.17
-- Versión de PHP: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `csi20mdj`
--
CREATE DATABASE IF NOT EXISTS `csi20mdj` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `csi20mdj`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caso`
--

CREATE TABLE `caso` (
  `Id` int(11) NOT NULL,
  `Id_Estado` int(11) NOT NULL,
  `Titulo` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Descripcion` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Importancia` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `caso`
--

INSERT INTO `caso` (`Id`, `Id_Estado`, `Titulo`, `Descripcion`, `Importancia`) VALUES
(1, 1, 'Desaparición hombre 30 añ', 'Desaparicion misteriosa', 5),
(2, 2, 'Asesinato persona', 'cuerpo sin identificar encontrado', 0),
(3, 3, 'Desaparición niña 1905', 'Niña de 15 años desaparecida', 9);

--
-- Disparadores `caso`
--
DELIMITER $$
CREATE TRIGGER `Caso_bi` BEFORE INSERT ON `caso` FOR EACH ROW begin
	if NEW.Titulo='' then
		signal sqlstate '45000' set
			message_text = 'El Titulo del caso no puede estar vacío.';
 	end if;
    if NEW.Descripcion='' then
		signal sqlstate '45000' set
			message_text = 'La Descripcion del caso no puede estar vacía.';
 	end if;
    if NEW.Importancia<1 then
		signal sqlstate '45000' set
			message_text = 'La Importancia del caso no debe ser menor que 0.';
 	end if;
end
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `Caso_bu` BEFORE UPDATE ON `caso` FOR EACH ROW begin
	if NEW.Titulo='' then
		signal sqlstate '45000' set
			message_text = 'El Titulo del caso no puede estar vacío.';
 	end if;
    if NEW.Descripcion='' then
		signal sqlstate '45000' set
			message_text = 'La Descripcion del caso no puede estar vacía.';
 	end if;
    if NEW.Importancia<0 then
		signal sqlstate '45000' set
			message_text = 'La Importancia del caso no debe ser menor que 1.';
 	end if;
end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado`
--

CREATE TABLE `estado` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `estado`
--

INSERT INTO `estado` (`Id`, `Nombre`) VALUES
(1, 'Abierto'),
(2, 'Cerrado'),
(3, 'Prescrito');

--
-- Disparadores `estado`
--
DELIMITER $$
CREATE TRIGGER `Estado_bi` BEFORE INSERT ON `estado` FOR EACH ROW begin
	if NEW.Nombre='' then
		signal sqlstate '45000' set
			message_text = 'El Nombre del estado no puede estar vacío.';
 	end if;
end
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `Estado_bu` BEFORE UPDATE ON `estado` FOR EACH ROW begin
	if NEW.Nombre='' then
		signal sqlstate '45000' set
			message_text = 'El Nombre del estado no puede estar vacío.';
 	end if;
end
$$
DELIMITER ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `caso`
--
ALTER TABLE `caso`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Titulo` (`Titulo`);

--
-- Indices de la tabla `estado`
--
ALTER TABLE `estado`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `caso`
--
ALTER TABLE `caso`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=250;

--
-- AUTO_INCREMENT de la tabla `estado`
--
ALTER TABLE `estado`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=349;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
