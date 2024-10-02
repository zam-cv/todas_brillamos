import {get, del, post, upload } from "../methods";

export interface Product {
    model: string;
    name: string;
    description: string;
    price: number;
    stock: number;
    size: string;
    color: string;
    maintenance: string;
    material: string;
    absorbency: string;
    material_feature: string;
    category_id: number;
}

export default {
    product: {
        setProduct: (
            file: File,
            product: Product

        ): Promise<void> => {
            return upload("/products/upload", file, product);
        },

        
        updateMetadataProduct : (
            file: File,
            product: Product,
            id:number
        ): Promise<void> => {
            return upload(`/products/${id}/metadata`, file, product, false);
        },

        addProductToCart: (
            file:File,
            product: Product,
            id:number,
            id_cart:number
        ): Promise<void> => {
            return upload(`/cart/${id}/${id_cart}`, file, product, false)
        },

        addProductToFavorites: (
            file: File,
            product: Product,
            id: number
        ): Promise<void> => {
            return upload(`/favorites/${id}`, file, product, false)
        },
        
        //GET
        getProducts: (): Promise<Product[]> => {
            return get("/products", false)
        },

        getProductImage: (): Promise<void> => {
            return get("uploads/products")
        },

        getProductFromCart: (id:number): Promise<void> => {
            return get(`/products/${id}`)
        },

        getProductFromFavorites: (): Promise<void> => {
            return get("/favorites")
        },

        //PUT
        updateProduct: (
            file: File,
            product: Product,
            id:number
        ): Promise<void> => {
            return upload(`/products/${id}`, file, product, false);
        },

        updateProdQuantityInCart: (
            file: File,
            product: Product,
            quantity: number,
            id:number
        ): Promise<void> => {
            return upload(`/cart/${id}/${quantity}`, file, product, false)
        },

        //DEL
        deleteProduct: (id: number): Promise<void> => {
            return del(`/products/${id}`)
        },

        deleteProductFromCart: (id:number): Promise<void> => {
            return del(`/cart/${id}`)
        },

        deleteProductFromFavorites: (id:number): Promise<void> => {
            return del(`/favorites/${id}`)
        }

    }
}