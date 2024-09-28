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
            product: Product
        ): Promise<void> => {
            return upload("admin/products/1/metadata", file, product, false);
        },

        addProductToCart: (
            file:File,
            product: Product
        ): Promise<void> => {
            return upload("admin/cart/2/3", file, product, false)
        },

        addProductToFavorites: (
            file: File,
            product: Product
        ): Promise<void> => {
            return upload("admin/favorites/1", file, product, false)
        },
        
        //GET
        getProducts: (): Promise<void> => {
            return get("admin/products")
        },

        getProductImage: (): Promise<void> => {
            return get("admin/products/{image}")
        },

        getProductFromCart: (): Promise<void> => {
            return get("admin/products/{cart}")
        },

        getProductFromFavorites: (): Promise<void> => {
            return get("admin/favorites")
        },

        //PUT
        updateProduct: (
            file: File,
            product: Product
        ): Promise<void> => {
            return upload("admin/products/1", file, product, false);
        },

        updateProdQuantityInCart: (
            file: File,
            product: Product
        ): Promise<void> => {
            return upload("admin/cart/1/1", file, product, false)
        },

        //DEL
        deleteProduct: (): Promise<void> => {
            return del("admin/products/2")
        },

        deleteProductFromCart: (): Promise<void> => {
            return del("admin/cart/2")
        },

        deleteProductFromFavorites: (): Promise<void> => {
            return del("admin/favorites/1")
        }

    }
}