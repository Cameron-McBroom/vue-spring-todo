import {AXIOS} from "./http-common"

export default {
    async getTaskById(id) {
        const response = await AXIOS.get(`api/task/${id}`);
        return response.data;
    },

    async getTasks() {
        const response = await AXIOS.get("api/task");
        return response.data;
    },

    async deleteTaskById(id) {
        const response = await AXIOS.delete(`api/task/${id}`);
        return response.data
    },

    async addNewTask(task) {
        const response = await AXIOS.post('api/task', {
            name: task.name
        })
        return response.data
    },

    async markComplete(id, complete) {
        const response = await AXIOS.put(`api/task/${id}/${complete.toLocaleString().toLocaleLowerCase()}`)
        return response.data;
    },

    async updateTaskName(id, newName) {
      const response = await AXIOS.post(`api/task/${id}/update`, {
          name: newName
      })
        return response.data

    }
}