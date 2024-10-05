import {z} from "zod"

export const taskSchema = z.object({
    amount: z.number(),
    description: z.string(),
    date: z.string(),
})

export type Task = z.infer<typeof taskSchema>