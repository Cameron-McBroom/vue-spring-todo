import {AXIOS} from "./http-common"

export default {
    async getTaskById(id) {
        const response = await AXIOS.get(`/task/${id}`);
        return response.data;
    },

    async getTasks() {
        const response = await AXIOS.get("/task");
        return response.data;
    },

    async deleteTaskById(id) {
        const response = await AXIOS.delete(`/task/${id}`);
        return response.data
    },

    async addNewTask(task) {
        const response = await AXIOS.post('/task', {
            name: task.name
        })
        return response.data
    },

    async markComplete(id, complete) {
        const response = await AXIOS.put(`/task/${id}/${complete.toLocaleString().toLocaleLowerCase()}`)
        return response.data;
    },

    async updateTaskName(id, newName) {
      const response = await AXIOS.post(`/task/${id}/update`, {
          name: newName
      })
        return response.data

    }
}