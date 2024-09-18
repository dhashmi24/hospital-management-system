CREATE TABLE `patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `age` int NOT NULL,
  `gender` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `doctors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `specialization` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `appointments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NOT NULL,
  `doctor_id` int NOT NULL,
  `appointment_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `patient_id` (`patient_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`),
  CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`)
);

