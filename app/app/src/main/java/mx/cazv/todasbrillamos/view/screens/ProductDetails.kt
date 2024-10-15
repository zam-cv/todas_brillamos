package mx.cazv.todasbrillamos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.cazv.todasbrillamos.R
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.models.ProductList
import mx.cazv.todasbrillamos.model.models.ProductRaw
import mx.cazv.todasbrillamos.model.states.RandomState
import mx.cazv.todasbrillamos.ui.theme.AccentColor
import mx.cazv.todasbrillamos.ui.theme.BackgroundColor
import mx.cazv.todasbrillamos.ui.theme.BadgePink
import mx.cazv.todasbrillamos.ui.theme.ImageBackgroundColor
import mx.cazv.todasbrillamos.ui.theme.SelectorsBackgroundColor
import mx.cazv.todasbrillamos.view.Routes
import mx.cazv.todasbrillamos.view.components.AlertDialogExample
import mx.cazv.todasbrillamos.view.components.Description
import mx.cazv.todasbrillamos.view.components.FavoriteComponent
import mx.cazv.todasbrillamos.view.components.Line
import mx.cazv.todasbrillamos.view.components.footer.ButtonBottomBar
import mx.cazv.todasbrillamos.view.components.header.BasicTopBar
import mx.cazv.todasbrillamos.view.layouts.CustomLayout
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.CartViewModel
import mx.cazv.todasbrillamos.viewmodel.FavoritesViewModel
import mx.cazv.todasbrillamos.viewmodel.ProductViewModel
import mx.cazv.todasbrillamos.viewmodel.UserViewModel

/**
 * Función que controla la cantidad de un producto en el carrito.
 * @author Carlos Zamudio
 *
 * @param value El valor actual de la cantidad.
 * @param onValueChange La función que se llama cuando se cambia el valor de la cantidad.
 * @param stock La cantidad de stock disponible del producto.
 */
@Composable
fun QuantityController(
    value: Int,
    onValueChange: (Int) -> Unit,
    stock: Int
) {
    val displayValue = if (value <= 0) 1 else value

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .wrapContentWidth()
            .background(SelectorsBackgroundColor)
            .padding(3.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        if (displayValue > 1) {
                            onValueChange(displayValue - 1)
                        }
                    }
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Text(text = "-")
            }

            Box(
                modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayValue.toString(),
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .clickable {
                        if (displayValue < stock) {
                            onValueChange(displayValue + 1)
                        }
                    }
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

/**
 * Composable que muestra un detalle específico del producto.
 * @author Carlos Zamudio
 *
 * @param name El nombre del detalle.
 * @param text El valor del detalle.
 */
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

/**
 * Composable que muestra los detalles del producto, incluyendo descripción y detalles específicos.
 * @author Carlos Zamudio
 *
 * @param product El producto [ProductRaw] del cual se mostrarán los detalles.
 */
@Composable
fun Details(product: ProductRaw) {
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
                    val descriptions = product.description.split("|").map { it.trim() }

                    Column {
                        descriptions.forEach { part ->
                            Description(text = part)
                        }
                    }
                }

                "details" -> {
                    Column {
                        Detail(name = "Marca", text = "ZAZIL")
                        Detail(name = "Tamaño", text = product.size)
                        Detail(name = "Mantenimiento", text = product.maintenance)
                        Detail(name = "Material", text = product.material)
                        Detail(name = "Flujo", text = product.absorbency)
                        Detail(name = "Cuidado de la piel", text = product.material_feature)
                        Detail(name = "Color", text = product.color)
                    }
                }
            }
        }
    }
}

/**
 * Composable que muestra un producto con su imagen, nombre, modelo, precio y descuento.
 *
 * @param name El nombre del producto.
 * @param model El modelo del producto.
 * @param price El precio del producto.
 * @param folder La carpeta donde se encuentra la imagen del producto.
 * @param hash El hash de la imagen del producto.
 * @param type El tipo de archivo de la imagen del producto.
 */
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
                val baseUrl = ApiConfig.BASE_URL
                val url = "$baseUrl$folder/$hash.$type"

                AsyncImage(
                    model = url,
                    contentDescription = "Product",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

/*            Row (
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
            }*/

            Spacer(modifier = Modifier.height(10.dp))

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

/*                    Text(
                        text = "\$000.00",
                        fontSize = 7.sp,
                        textDecoration = TextDecoration.LineThrough,
                        modifier = Modifier
                            .padding(top = 4.dp, end = 10.dp),
                        style = TextStyle(
                            baselineShift = BaselineShift(-1.4f)
                        )
                    )*/
                }
            }
        }
    }
}

/**
 * Composable que muestra una lista de productos adicionales.
 *
 * @param text El título de la sección.
 * @param products La lista de productos a mostrar.
 * @param navController El controlador de navegación de la aplicación.
 * @param modifier Modificador para personalizar la apariencia y el comportamiento del componente.
 */
@Composable
fun MoreProducts(
    text: String,
    products: ProductList,
    navController: NavHostController,
    modifier: Modifier,
) {
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
                Box (
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Routes.ROUTE_PRODUCT_DETAILS + "/${product.id}")
                        }
                ) {
                    Product(
                        name = product.name,
                        model = product.model,
                        price = product.price,
                        folder = products.folder,
                        hash = product.hash,
                        type = product.type,
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

/**
 * Pantalla de detalles del producto que muestra la información detallada del producto seleccionado.
 *
 * @param navController El controlador de navegación de la aplicación.
 * @param productId El ID del producto a mostrar.
 * @param randomState El estado de la lista de productos aleatorios.
 * @param authViewModel El ViewModel de autenticación.
 * @param userViewModel El ViewModel de usuario.
 * @param cartViewModel El ViewModel del carrito de compras.
 * @param favoritesViewModel El ViewModel de favoritos.
 * @param productViewModel El ViewModel de productos.
 */
@Composable
fun ProductDetails(
    navController: NavHostController,
    productId: Int,
    randomState: State<RandomState>,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel = FavoritesViewModel(),
    productViewModel: ProductViewModel = viewModel(),
) {
    var quantity by remember { mutableStateOf(1) }
    val productState = productViewModel.state.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var isProductInCart by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogText by remember { mutableStateOf("") }
    var dialogIcon by remember { mutableStateOf(Icons.Outlined.Info) }

    LaunchedEffect(key1 = Unit) {
        val token = authViewModel.token()

        if (token != null) {
            productViewModel.loadProduct(token, productId.toString())
            isProductInCart = cartViewModel.isProductInCart(productId)
        }
    }

    CustomLayout (
        navController = navController,
        topBar = {
            BasicTopBar(title = "Detalles del Producto", navController = navController)
        },
        bottomBar = {
            if (productState.value.product.product.stock == 0) {
                ButtonBottomBar(buttonText = "Producto no disponible", onClick = { }, barImage = R.drawable.bottom_bar_disabled)
            } else {
                ButtonBottomBar(buttonText = "Añadir al carrito", onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        val token = withContext(Dispatchers.IO) {
                            authViewModel.token()
                        }

                        if (token != null) {
                            cartViewModel.addProductToCart(
                                token,
                                productState.value.product.product,
                                quantity
                            )

                            if (isProductInCart) {
                                dialogTitle = "Producto ya agregado"
                                dialogText = "El producto ya se encuentra en el carrito"
                                dialogIcon = Icons.Outlined.Info
                            } else {
                                dialogTitle = "Producto agregado"
                                dialogText = "El producto se ha agregado al carrito"
                                dialogIcon = Icons.Outlined.Check
                            }

//                            navController.navigate(Routes.ROUTE_CART)
                            showDialog = true

                        }
                    }
                })
            }
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(BackgroundColor)
                .padding(top = 15.dp, start = 15.dp, bottom = 25.dp)
        ) {
            Column (
                modifier = Modifier
                    .padding(end = 15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(ImageBackgroundColor)
                ) {
                    val baseUrl = ApiConfig.BASE_URL
                    val folder = productState.value.product.folder
                    val hash = productState.value.product.product.hash
                    val type = productState.value.product.product.type
                    val url = "$baseUrl$folder/$hash.$type"

                    AsyncImage(
                        model = url,
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .offset(x = (-10).dp, y = 10.dp)
                    ) {
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            FavoriteComponent(
                                authViewModel,
                                favoritesViewModel,
                                productState.value.product.product
                            )
                        }
                    }
                }

                Text(
                    text = productState.value.product.product.name,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 15.dp)
                )

                Text(
                    text = productState.value.product.product.model,
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
                            text = "\$${productState.value.product.product.price}.00",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    QuantityController(
                        value = quantity,
                        onValueChange = { quantity = it },
                        stock = productState.value.product.product.stock
                    )
                }
            }

            Box {
                Column {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp)
                    ) {
                        Details(productState.value.product.product)
                    }

                    if (randomState.value.products.products.isNotEmpty()) {
                        MoreProducts(
                            text = "Más productos",
                            products = randomState.value.products,
                            navController = navController,
                            modifier = Modifier.padding(top = 60.dp)
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialogExample(
            onDismissRequest = { showDialog = false
                                 isProductInCart = true},
            onConfirmation = { showDialog = false
                                isProductInCart = true},
            dialogTitle = dialogTitle,
            dialogText = dialogText,
            icon = dialogIcon
        )
    }
}