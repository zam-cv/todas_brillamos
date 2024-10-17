import {get, del, post} from "../methods";

export interface Favorites{
    id: string;
    client_id: string;
    product_id: string;
}


export default {
    favorites: {
        getFavorites: (): Promise<void> => {
            return get("admin/favorites")
        },

        getProductFromFavorites: (id: number): Promise<void> => {
            return get(`admin/favorites/${id}`)
        },

        deleteProductFromFavorites: (id: number): Promise<void> => {
            return del(`admin/favorites/${id}`)
        },

        addProductToFavorites: (
            favorites: Favorites
        ): Promise<void> => {
            return post("admin/favorites", favorites)
        }
        
    }
}