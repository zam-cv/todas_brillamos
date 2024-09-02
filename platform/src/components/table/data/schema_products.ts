import {z} from "zod"

export const taskSchema = z.object({
    idProducto: z.string(),
    cantidad: z.string(),
    nombreProd: z.string(),
    precio: z.string(),
    marca: z.string(),
    tamano: z.string(),
    material: z.string(),
    absorbencia: z.string(),
    cuidadoPiel: z.string(),
    color: z.string(),
    descripcion: z.string()
})

export type Task = z.infer<typeof taskSchema>