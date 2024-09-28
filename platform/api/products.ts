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

        getProducts: (): Promise<void> => {
            return get("admin/products")
        },

        getProductImage: (): Promise<void> => {
            return get("admin/products/{image}")
        },

        getProductFromCart: (): Promise<void> => {
            return get("admin/products/{cart}")
        }



    }
}