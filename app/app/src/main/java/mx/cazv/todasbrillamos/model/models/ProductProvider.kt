package mx.cazv.todasbrillamos.model.models

import mx.cazv.todasbrillamos.R

/**
 * Lista para generar productos
 * @author: Min Che Kim
 */
class ProductProvider {
    companion object{
        val productList = listOf(
            Product(
                R.drawable.temp_img,
                "Ana Toalla Femenina Reutilizable",
                "Nocturna",
                180.00,
                null,
                true
            ),
            Product(
                R.drawable.temp_img,
                "MARGARITA Toalla Femenina Reutilizable",
                "Teen",
                130.00,
                null,
                true
            ),
            Product(
                R.drawable.temp_img,
                "VIOLETA Toalla Femenina Reutilizable",
                "Regular",
                150.00,
                null,
                true
            ),
            Product(
                R.drawable.temp_img,
                "KIT ZAZIL 2 AYNII",
                "",
                460.00,
                15.00,
                true
            ),
            Product(
                R.drawable.temp_img,
                "Ana Toalla Femenina Reutilizable",
                "Regular",
                180.00
            ),
            Product(
                R.drawable.temp_img,
                "KIT ZAZIL ARELI",
                "",
                610.00,
                15.00
            )

        )

        val favProductList = listOf(
            Product(
                R.drawable.temp_img,
                "Ana Toalla Femenina Reutilizable",
                "Nocturna",
                180.00,
                null,
                true
            ),
            Product(
                R.drawable.temp_img,
                "MARGARITA Toalla Femenina Reutilizable",
                "Teen",
                130.00,
                null,
                true
            ),
            Product(
                R.drawable.temp_img,
                "VIOLETA Toalla Femenina Reutilizable",
                "Regular",
                150.00,
                null,
                true
            ),
            Product(
                R.drawable.temp_img,
                "KIT ZAZIL 2 AYNII",
                "",
                460.00,
                15.00,
                true
            )

        )

    }


}