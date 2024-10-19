import {get, post } from "@/utils/methods";

/**
 * API para las categorías
 * @Author Sebastian Antonio Almanza
 */

// Interfaz para los datos de las categorías
export interface Category {
    id: number;
    name: string;
}


export default {
    category: {
        // Endpoint para agregar una categoría con solicitud POST
        setCategory: (
            category: Category
        ): Promise<Category> => {
            return post("/categories", category)
        },

        // Endpoint para obtener las categorías con solicitud
        getCategories: (): Promise<Category[]> => {
            return get("/categories")
        },

        // Endpoint para obtener una categoría con solicitud GET
        getCategory: (id: number): Promise<void> => {
            return get(`/categories/${id}`)
        }
    }
}