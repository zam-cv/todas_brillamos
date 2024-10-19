import {get, del, post, upload} from "@/utils/methods";

/**
 * API para el carrito de compras
 * @Author Sebastian Antonio Almanza
 */

// Interfaz para los datos del carrito
export interface Cart {
    quantity: number;
    product_id: number;
    client_id: number;
}

// Interfaz para los datos del producto en el carrito
export interface CartProduct {
    name: string;
    description: string;
    price: number;
    stock: number;
    category_id: number;    
}

export default {
    cart: {
        // Endpoint para obtener el carrito de compras
        getCart: (): Promise<void> => {
            return get("admin/cart")
        },

        // Endpoint para obtener un producto del carrito
        getProductFromCart: (id: number): Promise<void> => {
            return get(`admin/cart/exists/${id}`)
        },

        // Endpoint para actualizar la cantidad de un producto en el carrito
        updateProductQuantityInCart:(
            file: File,
            cartProduc: CartProduct,
        ): Promise<void> => {
            return upload("api/cart", file, cartProduc, false)
        },

        // Endpoint para eliminar un producto del carrito
        deleteProductFromCart: (id: number): Promise<void> => {
            return del(`admin/cart/${id}`)
        },

        // Endpoint para agregar un producto al carrito
        addProductToCart: (
            cartProduct: CartProduct
        ): Promise<void> => {
            return post("admin/cart", cartProduct)
        }


    }
}