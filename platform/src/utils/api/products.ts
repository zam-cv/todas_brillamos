import {get, del, upload } from "../methods";
import { SERVER } from "../constants";

export interface Product {
    id: number;
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

export interface ProductsInfo {
    folder: string;
    products: ProductRaw[];
}

export interface ProductRaw extends Product {
    hash: string;
    type: string;
}
export interface processedProduct extends Product {
    url: string;
}

export interface ProductoCreado{
    id: number;
    name: string;
}

export interface CategoryProducts {
    ID: number;
    CategoryName: string;
    ProductCount: number;
}

export default {
    product: {
        setProduct: (
            file: File,
            product: Product

        ): Promise<ProductoCreado> => {
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
        getProducts: async (): Promise<[Product[], string]> => {
            const prodInfo = await get<ProductsInfo>("/products")
            return [prodInfo.products.map((prod: ProductRaw) => ({
                ...prod,
                url: `${SERVER}/${prodInfo.folder}/${prod.hash}.${prod.type}`
            })), prodInfo.folder]
        
        },

    
        getProductFromCart: (id:number): Promise<void> => {
            return get(`/products/${id}`)
        },

        getProductFromFavorites: (): Promise<void> => {
            return get("/favorites")
        },

        getProductCategories: (): Promise<CategoryProducts[]> => {
            return get("/products/countCategories")
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
        },



    }
}