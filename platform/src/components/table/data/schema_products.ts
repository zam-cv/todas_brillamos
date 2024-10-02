import {z} from "zod"

export const taskSchema = z.object({
    model: z.string(),
    name: z.string(),
    description: z.string(),
    price: z.number(),
    stock: z.number(),
    size: z.string(),
    color: z.string(),
    maintenance: z.string(),
    material: z.string(),
    absorbency: z.string(),
    material_feature: z.string(),
    category_id: z.number(),
})

export type Task = z.infer<typeof taskSchema>