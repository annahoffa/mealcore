SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `mealcore`
--
SET FOREIGN_KEY_CHECKS = 0;
SET @tables = NULL;
SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables
FROM information_schema.tables
WHERE table_schema = (SELECT DATABASE())
AND LOWER(TABLE_NAME) NOT LIKE 'databasechangelog%';
SELECT IFNULL(@tables,'dummy') INTO @tables;

SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables);
PREPARE stmt FROM @tables;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET FOREIGN_KEY_CHECKS = 1;
--
-- Struktura tabeli dla tabeli `addition_5`
--

CREATE TABLE `addition_5` (
  `id` bigint(20) NOT NULL,
  `name` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(200) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `allergens_8`
--

CREATE TABLE `allergens_8` (
  `id` bigint(20) NOT NULL,
  `allergen` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `allergies_9`
--

CREATE TABLE `allergies_9` (
  `id` bigint(20) NOT NULL,
  `allergie` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `images_4`
--

CREATE TABLE `images_4` (
  `id` bigint(20) NOT NULL,
  `url` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `type` tinyint(1) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ingredients_3`
--

CREATE TABLE `ingredients_3` (
  `id` bigint(20) NOT NULL,
  `ingredients_text` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `allergens` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `traces_tags` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `additives_tags` varchar(400) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ingredients_from_palm_oil_tags` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ingredients_that_may_be_from_palm_oil_tags` varchar(400) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `nutrients_2`
--

CREATE TABLE `nutrients_2` (
  `id` bigint(20) NOT NULL,
  `energy_kj_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `energy_kcal_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `energy_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `energy_from_fat_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fat_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `saturated_fat_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `butyric_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `caproic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `caprylic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `capric_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `lauric_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `myristic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `palmitic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `stearic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `arachidic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `behenic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `lignoceric_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cerotic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `montanic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `melissic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `monounsaturated_fat_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `polyunsaturated_fat_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `omega_3_fat_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `alpha_linolenic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `eicosapentaenoic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `docosahexaenoic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `omega_6_fat_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `linoleic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `arachidonic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gamma_linolenic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dihomo_gamma_linolenic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `omega_9_fat_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `oleic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `elaidic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gondoic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mead_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `erucic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `nervonic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `trans_fat_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cholesterol_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `carbohydrates_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sugars_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sucrose_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `glucose_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fructose_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `lactose_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `maltose_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `maltodextrins_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `starch_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `polyols_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fiber_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `soluble_fiber_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `insoluble_fiber_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `proteins_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `casein_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `serum_proteins_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `nucleotides_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `salt_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sodium_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `alcohol_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `vitamin_a_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `beta_carotene_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `vitamin_d_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `vitamin_e_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `vitamin_k_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `vitamin_c_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `vitamin_b1_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `vitamin_b2_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `vitamin_pp_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `vitamin_b6_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `vitamin_b9_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `folates_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `vitamin_b12_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `biotin_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `pantothenic_acid_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `silica_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bicarbonate_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `potassium_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `chloride_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `calcium_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phosphorus_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `iron_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `magnesium_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `zinc_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `copper_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `manganese_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fluoride_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `selenium_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `chromium_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `molybdenum_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `iodine_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `caffeine_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `taurine_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ph_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fruits_vegetables_nuts_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fruits_vegetables_nuts_dried_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fruits_vegetables_nuts_estimate_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `collagen_meat_protein_ratio_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cocoa_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `chlorophyl_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `carbon_footprint_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `carbon_footprint_from_meat_or_fish_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `glycemic_index_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `water_hardness_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `choline_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phylloquinone_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `beta_glucan_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `inositol_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `carnitine_100g` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `products_1`
--

CREATE TABLE `products_1` (
  `id` bigint(20) NOT NULL,
  `code` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `product_name` varchar(400) COLLATE utf8mb4_general_ci NOT NULL,
  `brands` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `countries_tags` varchar(3000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `serving_quantity` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `serving_size` varchar(300) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `emb_codes_tags` varchar(400) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `quantity` varchar(300) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `sports_11`
--

CREATE TABLE `sports_11` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `kcal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users_6`
--

CREATE TABLE `users_6` (
  `id` bigint(20) NOT NULL,
  `login` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `account_type` tinyint(1) DEFAULT NULL,
  `sex` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `height` float DEFAULT NULL,
  `exercise_type` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users_exercises_12`
--

CREATE TABLE `users_exercises_12` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `sport_id` bigint(20) NOT NULL,
  `date` date NOT NULL,
  `duration` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users_products_10`
--

CREATE TABLE `users_products_10` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `addition_5`
--
ALTER TABLE `addition_5`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `allergens_8`
--
ALTER TABLE `allergens_8`
  ADD PRIMARY KEY (`id`),
  ADD KEY `allergens_8_ibfk_1` (`user_id`);

--
-- Indeksy dla tabeli `allergies_9`
--
ALTER TABLE `allergies_9`
  ADD PRIMARY KEY (`id`),
  ADD KEY `allergies_9_ibfk_1` (`user_id`);

--
-- Indeksy dla tabeli `images_4`
--
ALTER TABLE `images_4`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indeksy dla tabeli `ingredients_3`
--
ALTER TABLE `ingredients_3`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `product_id` (`product_id`) USING BTREE;

--
-- Indeksy dla tabeli `nutrients_2`
--
ALTER TABLE `nutrients_2`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `product_id` (`product_id`) USING BTREE;

--
-- Indeksy dla tabeli `products_1`
--
ALTER TABLE `products_1`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `sports_11`
--
ALTER TABLE `sports_11`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `users_6`
--
ALTER TABLE `users_6`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`);

--
-- Indeksy dla tabeli `users_exercises_12`
--
ALTER TABLE `users_exercises_12`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `sport_id` (`sport_id`);

--
-- Indeksy dla tabeli `users_products_10`
--
ALTER TABLE `users_products_10`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `addition_5`
--
ALTER TABLE `addition_5`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `allergens_8`
--
ALTER TABLE `allergens_8`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `allergies_9`
--
ALTER TABLE `allergies_9`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `images_4`
--
ALTER TABLE `images_4`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `ingredients_3`
--
ALTER TABLE `ingredients_3`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `nutrients_2`
--
ALTER TABLE `nutrients_2`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `products_1`
--
ALTER TABLE `products_1`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `sports_11`
--
ALTER TABLE `sports_11`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `users_6`
--
ALTER TABLE `users_6`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `users_exercises_12`
--
ALTER TABLE `users_exercises_12`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `users_products_10`
--
ALTER TABLE `users_products_10`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `allergens_8`
--
ALTER TABLE `allergens_8`
  ADD CONSTRAINT `allergens_8_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users_6` (`id`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `allergies_9`
--
ALTER TABLE `allergies_9`
  ADD CONSTRAINT `allergies_9_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users_6` (`id`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `images_4`
--
ALTER TABLE `images_4`
  ADD CONSTRAINT `images_4_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products_1` (`id`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `ingredients_3`
--
ALTER TABLE `ingredients_3`
  ADD CONSTRAINT `ingredients_3_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products_1` (`id`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `nutrients_2`
--
ALTER TABLE `nutrients_2`
  ADD CONSTRAINT `nutrients_2_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products_1` (`id`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `users_exercises_12`
--
ALTER TABLE `users_exercises_12`
  ADD CONSTRAINT `users_exercises_12_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users_6` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `users_exercises_12_ibfk_2` FOREIGN KEY (`sport_id`) REFERENCES `sports_11` (`id`);

--
-- Ograniczenia dla tabeli `users_products_10`
--
ALTER TABLE `users_products_10`
  ADD CONSTRAINT `users_products_10_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products_1` (`id`),
  ADD CONSTRAINT `users_products_10_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users_6` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
