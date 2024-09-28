import {get, del, post, upload } from "../src/utils/methods";

export interface Product {
    model: string;
    name: string;
    description: string;
    price: number;
    stock: number;
    size: string;
    color: string;
    manteinance: string;
    material: string;
    absorbency: string;
    skin_care: string;
    category_id: number;

}

export default {
    products: {
        setProduct: (
            file: File,
            product: Product

        ): Promise<void> => {
            return upload("admin/products/upload", file, product, false);
        },

        
        updateMetadataProduct : (
            file: File,
            product: Product,
            id:number
        ): Promise<void> => {
            return upload(`admin/products/${id}/metadata`, file, product, false);
        },

        addProductToCart: (
            file:File,
            product: Product,
            id:number,
            id_cart:number
        ): Promise<void> => {
            return upload(`admin/cart/${id}/${id_cart}`, file, product, false)
        },

        addProductToFavorites: (
            file: File,
            product: Product,
            id: number
        ): Promise<void> => {
            return upload(`admin/favorites/${id}`, file, product, false)
        },
        
        //GET
        getProducts: (): Promise<void> => {
            return get("admin/products")
        },

        getProductImage: (): Promise<void> => {
            return get("uploads/products")
        },

        getProductFromCart: (id:number): Promise<void> => {
            return get(`admin/products/${id}`)
        },

        getProductFromFavorites: (): Promise<void> => {
            return get("admin/favorites")
        },

        //PUT
        updateProduct: (
            file: File,
            product: Product,
            id:number
        ): Promise<void> => {
            return upload(`admin/products/${id}`, file, product, false);
        },

        updateProdQuantityInCart: (
            file: File,
            product: Product,
            quantity: number,
            id:number
        ): Promise<void> => {
            return upload(`admin/cart/${id}/${quantity}`, file, product, false)
        },

        //DEL
        deleteProduct: (id: number): Promise<void> => {
            return del(`admin/products/${id}`)
        },

        deleteProductFromCart: (id:number): Promise<void> => {
            return del(`admin/cart/${id}`)
        },

        deleteProductFromFavorites: (id:number): Promise<void> => {
            return del(`admin/favorites/${id}`)
        }

    }
}