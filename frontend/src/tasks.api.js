import axios from "axios";

export default {
    async getTaskById(id) {
        const response = await axios.get(`api/task/${id}`);
        return response.data;
    },

    async getTasks() {
        const response = await axios.get("api/task");
        return response.data;
    },

    async deleteTaskById(id) {
        const response = await axios.delete(`api/task/${id}`);
        return response.data
    },

    async addNewTask(task) {
        const response = await axios.post('api/task', {
            name: task.name
        })
        return response.data
    },

    async markComplete(id, complete) {
        const response = await axios.put(`api/task/${id}/${complete.toLocaleString().toLocaleLowerCase()}`)
        return response.data;
    },

    async updateTaskName(id, newName) {
      const response = await axios.post(`api/task/${id}/update`, {
          name: newName
      })
        return response.data

    }
}