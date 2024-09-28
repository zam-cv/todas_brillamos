import {get, del, post, upload } from "../src/utils/methods";

export interface Category {
    name: string;
}

export default {
    category: {
        setCategory: (
            file: File,
            category: Category
        ): Promise<void> => {
            return post("admin/categories", category)
        },

        getCategories: (): Promise<void> => {
            return get("admin/categories")
        },


        getCategory: (id: number): Promise<void> => {
            return get(`admin/categories/${id}`)
        }
    }
}