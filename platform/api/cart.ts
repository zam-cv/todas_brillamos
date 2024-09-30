import {get, del, post, upload} from "../src/utils/methods";

export interface Cart {
    quantity: number;
    product_id: number;
    client_id: number;
}

export interface CartProduct {
    name: string;
    description: string;
    price: number;
    stock: number;
    category_id: number;    
}

export default {
    cart: {
        getCart: (): Promise<void> => {
            return get("admin/cart")
        },

        getProductFromCart: (id: number): Promise<void> => {
            return get(`admin/cart/exists/${id}`)
        },

        updateProductQuantityInCart:(
            file: File,
            cartProduc: CartProduct,
        ): Promise<void> => {
            return upload("api/cart", file, cartProduc, false)
        },

        deleteProductFromCart: (id: number): Promise<void> => {
            return del(`admin/cart/${id}`)
        },

        addProductToCart: (
            cartProduct: CartProduct
        ): Promise<void> => {
            return post("admin/cart", cartProduct)
        }


    }
}