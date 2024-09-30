import {get, del, post, upload } from "../src/utils/methods";

export interface Posts {
    title: string;
    author: string;
    date: string;
    content: string
}


export default {
    posts: {
        setPost: (
            posts: Posts
        ): Promise<void> => {
            return post("admin/posts", posts)
        },

        getPosts: (): Promise<void> =>{
            return get("admin/posts")
        },

        deletePost: (id: number): Promise<void> => {
            return del(`admin/posts7${id}`)
        }

    }
}