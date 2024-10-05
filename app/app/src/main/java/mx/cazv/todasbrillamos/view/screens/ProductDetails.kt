package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.models.ProductList
import mx.cazv.todasbrillamos.model.models.ProductRaw
import mx.cazv.todasbrillamos.ui.theme.AccentColor
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.ui.theme.BadgePink
import mx.cazv.todasbrillamos.ui.theme.ImageBackgroundColor
import mx.cazv.todasbrillamos.ui.theme.SelectorsBackgroundColor
import mx.cazv.todasbrillamos.view.components.footer.BottomBar
import mx.cazv.todasbrillamos.view.components.header.CustomTopBar
import mx.cazv.todasbrillamos.view.components.Description
import mx.cazv.todasbrillamos.view.components.Line
import mx.cazv.todasbrillamos.view.layouts.CustomLayout

@Composable
fun QuantityController() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .wrapContentWidth()
            .background(SelectorsBackgroundColor)
            .padding(3.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box (
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = "-",
                )
            }

            Box (
                modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "0",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box (
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .padding(start = 6.dp, end = 6.dp)
            ) {
                Text(
                    text = "+",
                    fontSize = 13.sp,
                )
            }
        }
    }
}

@Composable
fun Detail(name: String, text: String) {
    Column {
        Row (
            modifier = Modifier
                .padding(bottom = 4.dp, top = 5.dp)
        ) {
            Text(
                text = name,
                fontSize = 13.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(start = 10.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = text,
                fontSize = 13.sp,
                modifier = Modifier.padding(end = 10.dp)
            )
        }

        Line(height = 0.5.dp)
    }
}

@Composable
fun ProductDetails() {
    var section by remember { mutableStateOf("description") }

    Column {
        Row (
            modifier = Modifier
                .padding(top = 15.dp)
        ) {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column {
                    TextButton(onClick = {
                        section = "description"
                    }) {
                        Text(
                            text = "Descripción",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W400,
                            textAlign = TextAlign.Center,
                            color = if (section == "description") AccentColor else Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    Line(color = if (section == "description") AccentColor else Color.Gray)
                }
            }

            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column {
                    TextButton(onClick = {
                        section = "details"
                    }) {
                        Text(
                            text = "Detalles",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W400,
                            textAlign = TextAlign.Center,
                            color = if (section == "details") AccentColor else Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    Line(color = if (section == "details") AccentColor else Color.Gray)
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(top = 15.dp)
        ) {
            when (section) {
                "description" -> {
                    Column {
                        Description(text = "Toalla sanitaria reutilizable para quienes buscan comodidad y seguridad durante su ciclo menstrual.")
                        Description(text = "Confeccionada a mano con algodón hipoalergénico, es suave al contacto con la piel y reduce el riesgo de irritaciones.")
                        Description(text = "Los broches de plástico en las alas proporcionan un ajuste firme, ofreciendo mayor tranquilidad y seguridad.")
                        Description(text = "Lavable y reutilizable, es una opción práctica y ecológica.")
                    }
                }

                "details" -> {
                    Column {
                        Detail(name = "Marca", text = "ZAZIL")
                        Detail(name = "Tamaño", text = "27.7 cm")
                        Detail(name = "Mantenimiento", text = "Lavable y reutilizable")
                        Detail(name = "Material", text = "Algodón")
                        Detail(name = "Absorbencia", text = "Alta")
                        Detail(name = "Cuidado de la piel", text = "Hipoalergénica, transpirable")
                        Detail(name = "Color", text = "Rojo")
                    }
                }
            }
        }
    }
}

@Composable
fun Product(
    name: String,
    model: String,
    price: Int,
    folder: String,
    hash: String,
    type: String
) {
    Box (
        modifier = Modifier
            .background(Color.White)
            .clip(RoundedCornerShape(5.dp))
    ) {
        Column {
            Box (
                modifier = Modifier
                    .width(150.dp)
                    .height(100.dp)
                    .background(ImageBackgroundColor)
            ) {
                val base_url = ApiConfig.BASE_URL
                val url = "$base_url$folder/$hash.$type"
                println(url)

                AsyncImage(
                    model = url,
                    contentDescription = "Product",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Row (
                modifier = Modifier
                    .padding(top = 7.dp, bottom = 7.dp)
                    .padding(start = 7.dp, end = 7.dp)
            ) {
                Box (
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(BadgePink)
                ) {
                    Text(
                        text = "-99%",
                        fontSize = 9.sp,
                        style = TextStyle(
                            baselineShift = BaselineShift(0f)
                        ),
                        modifier = Modifier
                            .padding(start = 5.dp, end = 5.dp, top = 4.dp, bottom = 4.dp)
                    )
                }

                Text(
                    text = "Promoción",
                    fontSize = 12.sp,
                    color = AccentColor,
                    style = TextStyle(
                        baselineShift = BaselineShift(0f)
                    ),
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 1.dp)
                )
            }

            Column (
                modifier = Modifier
                    .padding(start = 7.dp, end = 7.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.W400,
                    style = TextStyle(
                        baselineShift = BaselineShift(0f)
                    ),
                    modifier = Modifier
                        .padding(top = 2.dp)
                )

                Text(
                    text = model,
                    fontSize = 10.sp,
                    style = TextStyle(
                        baselineShift = BaselineShift(0f)
                    )
                )

                Row (
                    modifier = Modifier
                        .padding(bottom = 3.dp)
                ) {
                    Text(
                        text = "\$${price}.00",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 4.dp, end = 10.dp)
                    )

                    Text(
                        text = "\$000.00",
                        fontSize = 7.sp,
                        textDecoration = TextDecoration.LineThrough,
                        modifier = Modifier
                            .padding(top = 4.dp, end = 10.dp),
                        style = TextStyle(
                            baselineShift = BaselineShift(-1.4f)
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun MoreProducts(text: String, products: ProductList, modifier: Modifier) {
    Column (
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Row (
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            for (product in products.products) {
                Product(
                    name = product.name,
                    model = product.model,
                    price = product.price,
                    folder = products.folder,
                    hash = product.hash,
                    type = product.type,
                )

                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Composable
fun ProductDetails(navController: NavHostController) {
    CustomLayout (
        navController = navController,
        topBar = {
            CustomTopBar {
                Text(text = "Custom Top Bar")
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(BackgroundColor)
                .padding(top = 15.dp, start = 15.dp, bottom = 25.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column (
                modifier = Modifier
                    .padding(end = 15.dp)
            ) {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(ImageBackgroundColor)
                    .padding(10.dp)
                ) {
                    Box (
                        modifier = Modifier
                            .offset(x = 0.dp, y = 0.dp)
                    ) {
                        Row {
                            Spacer(modifier = Modifier.weight(1f))

                            Box (
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White)
                                    .padding(5.dp)
                            ) {
                                Icon(
                                    Icons.Outlined.Bookmark,
                                    contentDescription = "Bookmark",
                                    tint = AccentColor,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                }

                Text(
                    text = "Producto",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 15.dp)
                )

                Text(
                    text = "SKU: 0017",
                    fontSize = 13.sp,
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                    ) {
                        Text(
                            text = "\$1,000.00",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    QuantityController()
                }
            }

            Box {
                Column {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp)
                    ) {
                        ProductDetails()
                    }

/*                    MoreProducts(text = "Más productos", modifier = Modifier.padding(top = 60.dp))*/
                }
            }
        }
    }
}