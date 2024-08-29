import {z} from "zod"

export const taskSchema = z.object({
    idProducto: z.string(),
    cantidad: z.string(),
    nombreProd: z.string()
})

export type Task = z.infer<typeof taskSchema>