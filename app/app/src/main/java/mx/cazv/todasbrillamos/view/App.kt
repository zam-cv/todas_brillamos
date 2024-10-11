package mx.cazv.todasbrillamos.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.cazv.todasbrillamos.view.components.LoadingScreen
import mx.cazv.todasbrillamos.view.screens.Chat
import mx.cazv.todasbrillamos.view.screens.Favorites
import mx.cazv.todasbrillamos.view.screens.ForgotPassword
import mx.cazv.todasbrillamos.view.screens.home.Home
import mx.cazv.todasbrillamos.view.screens.Login
import mx.cazv.todasbrillamos.view.screens.Notifications
import mx.cazv.todasbrillamos.view.screens.Register
import mx.cazv.todasbrillamos.view.screens.Cart
import mx.cazv.todasbrillamos.view.screens.Orders
import mx.cazv.todasbrillamos.view.screens.Payments
import mx.cazv.todasbrillamos.view.screens.PostView
import mx.cazv.todasbrillamos.view.screens.TrackOrder
import mx.cazv.todasbrillamos.view.screens.config.Config
import mx.cazv.todasbrillamos.view.screens.ProductDetails
import mx.cazv.todasbrillamos.view.screens.ShippingInfo
import mx.cazv.todasbrillamos.view.screens.Store
import mx.cazv.todasbrillamos.view.screens.calendar.Calendar
import mx.cazv.todasbrillamos.view.screens.calendar.YourCycle
import mx.cazv.todasbrillamos.view.screens.config.About
import mx.cazv.todasbrillamos.view.screens.config.ChangePassword
import mx.cazv.todasbrillamos.view.screens.config.EditProfile
import mx.cazv.todasbrillamos.view.screens.config.SocialNetworks
import mx.cazv.todasbrillamos.view.screens.config.TermsAndPolicies
import mx.cazv.todasbrillamos.viewmodel.AuthState
import mx.cazv.todasbrillamos.viewmodel.AuthViewModel
import mx.cazv.todasbrillamos.viewmodel.BuyViewModel
import mx.cazv.todasbrillamos.viewmodel.CalendarVM
import mx.cazv.todasbrillamos.viewmodel.CartViewModel
import mx.cazv.todasbrillamos.viewmodel.ChatViewModel
import mx.cazv.todasbrillamos.viewmodel.FavoritesViewModel
import mx.cazv.todasbrillamos.viewmodel.PostsViewModel
import mx.cazv.todasbrillamos.viewmodel.ProductsViewModel
import mx.cazv.todasbrillamos.viewmodel.RandomViewModel
import mx.cazv.todasbrillamos.viewmodel.UserViewModel

/**
 * Archivo principal de la aplicación.
 * @author Carlos Zamudio
 */

/**
 * Composable principal de la aplicación que inicializa el controlador de navegación.
 */
@Composable
fun App() {
    val navController = rememberNavController()
    Nav(navController)
}

/**
 * Composable que define la navegación de la aplicación.
 *
 * @param navController El controlador de navegación.
 * @param authViewModel El ViewModel de autenticación.
 * @param userViewModel El ViewModel de usuario.
 * @param randomViewModel El ViewModel de productos aleatorios.
 * @param postsViewModel El ViewModel de publicaciones.
 * @param productsViewModel El ViewModel de productos.
 * @param cartViewModel El ViewModel de carrito.
 * @param chatViewModel El ViewModel de chat.
 * @param buyViewModel El ViewModel de compra.
 * @param modifier El modificador para personalizar la apariencia y el comportamiento del componente.
 */
@Composable
fun Nav(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    userViewModel: UserViewModel = UserViewModel(),
    randomViewModel: RandomViewModel = RandomViewModel(),
    postsViewModel: PostsViewModel = PostsViewModel(),
    productsViewModel: ProductsViewModel = ProductsViewModel(),
    cartViewModel: CartViewModel = CartViewModel(),
    chatViewModel: ChatViewModel = ChatViewModel(),
    buyViewModel: BuyViewModel = BuyViewModel(),
    favoritesViewModel: FavoritesViewModel = FavoritesViewModel(),
    modifier: Modifier = Modifier
) {
    var startDestination by remember { mutableStateOf<String?>(null) }
    val calendarVM: CalendarVM = viewModel()
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        startDestination = if (authViewModel.verify()) {
            Routes.ROUTE_HOME
        } else {
            Routes.ROUTE_LOGIN
        }
    }

    if (startDestination != null) {
        NavHost(
            navController = navController,
            startDestination = startDestination!!,
            modifier = modifier.fillMaxSize()
        ) {
            // Rutas públicas (siempre accesibles)
            composable(Routes.ROUTE_LOGIN) {
                Login(navController, authViewModel)
            }
            composable(Routes.ROUTE_REGISTER) {
                Register(navController, authViewModel)
            }
            composable(Routes.ROUTE_FORGOT_PASSWORD) {
                ForgotPassword(navController)
            }

            // Rutas protegidas (accesibles cuando el usuario está autenticado)
            val protectedRoutes = listOf(
                Routes.ROUTE_HOME,
                Routes.ROUTE_POST + "/{postId}", // Ruta dinamica
                Routes.ROUTE_STORE,
                Routes.ROUTE_CALENDAR,
                Routes.ROUTE_CHAT,
                Routes.ROUTE_FAVORITES,
                Routes.ROUTE_NOTIFICATIONS,
                Routes.ROUTE_CART,
                Routes.ROUTE_PRODUCT_DETAILS + "/{productId}", // Ruta dinamica
                Routes.ROUTE_YOUR_CYCLE,
                Routes.ROUTE_TRACK_ORDER,
                Routes.ROUTE_ORDERS,
                Routes.ROUTE_CONFIG,
                Routes.ROUTE_EDIT_PROFILE,
                Routes.ROUTE_CHANGE_PASSWORD,
                Routes.ROUTE_SOCIAL_NETWORKS,
                Routes.ROUTE_TERMS_AND_POLICIES,
                Routes.ROUTE_ABOUT,
                Routes.ROUTE_SHIPPING_INFO,
                Routes.ROUTE_PAYMENTS
            )

            protectedRoutes.forEach { route ->
                composable(route) { backStackEntry ->
                    if (authState is AuthState.SignInSuccess) {
                        val token = (authState as AuthState.SignInSuccess).credentials.token

                        // Data
                        val randomState = randomViewModel.state.collectAsState()

                        // Load data
                        LaunchedEffect(key1 = Unit) {
                            randomViewModel.loadRandomInfo(token)
                        }

                        when (route) {
                            Routes.ROUTE_HOME -> Home(navController, authViewModel, userViewModel, postsViewModel, randomState)
                            Routes.ROUTE_POST + "/{postId}" -> {
                                val postId = backStackEntry.arguments?.getString("postId")?.toInt()
                                if (postId != null) {
                                    PostView(navController, authViewModel, postsViewModel, postId)
                                }
                            }
                            Routes.ROUTE_STORE -> Store(navController, authViewModel, productsViewModel)
                            Routes.ROUTE_CALENDAR -> Calendar(navController, calendarVM)
                            Routes.ROUTE_CHAT -> Chat(navController, authViewModel, chatViewModel)
                            Routes.ROUTE_FAVORITES -> Favorites(navController, authViewModel, favoritesViewModel)
                            Routes.ROUTE_NOTIFICATIONS -> Notifications(navController)
                            Routes.ROUTE_CART -> Cart(navController, authViewModel, cartViewModel, userViewModel, buyViewModel)
                            Routes.ROUTE_PRODUCT_DETAILS + "/{productId}" -> {
                                val productId = backStackEntry.arguments?.getString("productId")
                                val id = productId?.toInt()

                                if (id != null) {
                                    ProductDetails(
                                        navController,
                                        id,
                                        randomState,
                                        authViewModel,
                                        cartViewModel,
                                        favoritesViewModel
                                    )
                                }
                            }
                            Routes.ROUTE_YOUR_CYCLE -> YourCycle(navController, calendarVM)
                            Routes.ROUTE_TRACK_ORDER -> TrackOrder(navController)
                            Routes.ROUTE_ORDERS -> Orders(navController)
                            Routes.ROUTE_CONFIG -> Config(navController, authViewModel)
                            Routes.ROUTE_EDIT_PROFILE -> EditProfile(navController, authViewModel, userViewModel)
                            Routes.ROUTE_CHANGE_PASSWORD -> ChangePassword(navController, authViewModel, userViewModel)
                            Routes.ROUTE_SOCIAL_NETWORKS -> SocialNetworks(navController)
                            Routes.ROUTE_TERMS_AND_POLICIES -> TermsAndPolicies(navController)
                            Routes.ROUTE_ABOUT -> About(navController)
                            Routes.ROUTE_SHIPPING_INFO -> ShippingInfo(
                                navController,
                                authViewModel,
                                userViewModel,
                            )
                            Routes.ROUTE_PAYMENTS -> {
                                Payments(navController, authViewModel, cartViewModel, buyViewModel)
                            }
                        }
                    } else {
                        // Redireccion a login si no está autenticado.
                        LaunchedEffect(Unit) {
                            navController.navigate(Routes.ROUTE_LOGIN) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }
        }
    } else {
        LoadingScreen()
    }
}