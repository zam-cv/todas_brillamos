import {z} from "zod"

export const taskSchema = z.object({
    quantity: z.number(),
    delivery_date: z.string(),
    status: z.string(),
    product_id: z.number(),
    client_id: z.number(),
   

})

export type Task = z.infer<typeof taskSchema>