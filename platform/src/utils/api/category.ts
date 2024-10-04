import {get, post } from "@/utils/methods";

export interface Category {
    id: number;
    name: string;
}


export default {
    category: {
        setCategory: (
            category: Category
        ): Promise<Category> => {
            return post("/categories", category)
        },

        getCategories: (): Promise<Category[]> => {
            return get("/categories")
        },


        getCategory: (id: number): Promise<void> => {
            return get(`/categories/${id}`)
        }
    }
}