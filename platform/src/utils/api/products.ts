import {get, del, upload, post} from "../methods";
import { SERVER } from "../constants";

/**
 * API para la información de los productos
 * @Author Sebastian Antonio Almanza
 */

// Interfaz para los datos de los productos
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

// Interfaz para los datos de los productos procesados
export interface ProductsInfo {
    folder: string;
    products: ProductRaw[];
}

// Interfaz para los datos de los productos crudos
export interface ProductRaw extends Product {
    hash: string;
    type: string;

}

// Interfaz para los datos de los productos procesados
export interface processedProduct extends Product {
    url: string;
}

// Interfaz para los datos de los productos creados
export interface ProductoCreado{
    id: number;
    name: string;
}

// Interfaz para los datos de los productos en categorias
export interface CategoryProducts {
    ID: number;
    category_name: string;
    product_count: number;
}

export default {
    product: {
        // endpoint para subir un producto a la base de datos
        setProduct: (
            file: File,
            product: Product

        ): Promise<ProductoCreado> => {
            return upload("/products/upload", file, product);
        },

        // endpoint para actualizar los datos de un producto
        updateMetadataProduct : (
            id:number,
            product: Product
        ): Promise<ProductoCreado> => {
            return post(`/products/${id}/metadata`, product);
        },

        // endpoint para añadir un producto al carrito
        addProductToCart: (
            file:File,
            product: Product,
            id:number,
            id_cart:number
        ): Promise<void> => {
            return upload(`/cart/${id}/${id_cart}`, file, product, false)
        },

        // endpoint para añadir un producto a favoritos
        addProductToFavorites: (
            file: File,
            product: Product,
            id: number
        ): Promise<void> => {
            return upload(`/favorites/${id}`, file, product, false)
        },
        
        //endpoint para obtener los productos
        getProducts: async (): Promise<[Product[], string]> => {
            const prodInfo = await get<ProductsInfo>("/products")
            return [prodInfo.products.map((prod: ProductRaw) => ({
                ...prod,
                url: `${SERVER}/${prodInfo.folder}/${prod.hash}.${prod.type}`
            })), prodInfo.folder]
        
        },

        //endpoint para obtener un producto del carrito
        getProductFromCart: (id:number): Promise<void> => {
             return get(`/products/${id}`)
        },

        // endpoint para obtener un producto de los favoritos
        getProductFromFavorites: (): Promise<void> => {
            return get("/favorites")
        },

        // endpoint para obtener los productos por categoria 
        getProductCategories: (): Promise<CategoryProducts[]> => {
            return get("/products/countCategories")
        },

        //Endpoint para actualizar la información de los productos
        updateProduct: (
            file: File,
            product: Product,
            id:number
        ): Promise<void> => {
            return upload(`/products/${id}`, file, product, false);
        },

        //Endpoint para actualizar la cantidad de un producto en el carrito
        updateProdQuantityInCart: (
            file: File,
            product: Product,
            quantity: number,
            id:number
        ): Promise<void> => {
            return upload(`/cart/${id}/${quantity}`, file, product, false)
        },

        //endpoint para eliminar un producto
        deleteProduct: (id: number): Promise<void> => {
            return del(`/products/${id}`)
        },

        //endpoint para eliminar un producto del carrito
        deleteProductFromCart: (id:number): Promise<void> => {
            return del(`/cart/${id}`)
        },

        //endpoint para eliminar un producto de los favoritos
        deleteProductFromFavorites: (id:number): Promise<void> => {
            return del(`/favorites/${id}`)
        },



    }
}