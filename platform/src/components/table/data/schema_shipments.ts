import {z} from "zod"

export const taskSchema = z.object({
    Quantity: z.number(),
    DeliveryDate: z.string(),
    Status: z.string(),
    ProductID: z.number(),
    ClientID: z.number(),

})

export type Task = z.infer<typeof taskSchema>