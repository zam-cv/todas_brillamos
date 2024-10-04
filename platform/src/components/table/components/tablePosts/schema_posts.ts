import {z} from "zod"

export const taskSchema = z.object({
    title: z.string(),
    author: z.string(),
    date: z.string(),
})

export type Task = z.infer<typeof taskSchema>