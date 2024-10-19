import {get, del, post} from "../methods";

/**
 * API para la información de los favoritos
 * @Author Sebastian Antonio Almanza
 */

// Interfaz para los datos de los favoritos
export interface Favorites{
    id: string;
    client_id: string;
    product_id: string;
}


export default {
    favorites: {
        // Endpoint para obtener los favoritos
        getFavorites: (): Promise<void> => {
            return get("admin/favorites")
        },
        // Endpoint para obtener un producto de los favoritos
        getProductFromFavorites: (id: number): Promise<void> => {
            return get(`admin/favorites/${id}`)
        },
        // Endpoint para eliminar un producto de los favoritos
        deleteProductFromFavorites: (id: number): Promise<void> => {
            return del(`admin/favorites/${id}`)
        },
        // Endpoint para agregar un producto a los favoritos
        addProductToFavorites: (
            favorites: Favorites
        ): Promise<void> => {
            return post("admin/favorites", favorites)
        }
        
    }
}