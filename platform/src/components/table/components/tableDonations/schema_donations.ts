import {z} from "zod"

export const taskSchema = z.object({
    product_name: z.string(),
    amount: z.number(),
    user_email: z.string(),
    first_name: z.string(),
    last_name: z.string(),
    curp: z.string()

})

export type Task = z.infer<typeof taskSchema>