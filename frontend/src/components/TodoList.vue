<template>
  <div class="Todo-List">
    <h1>Vue Spring Todo List</h1>
    <div class="new-item">
      <input
          v-model="newTodo"
          ref="newTask"
          class="new-item-input"
          type="text"
          placeholder="Add a new todo.."
          @keypress.enter="addNewTodo"
      />

      <div class="new-item-button" @click="addNewTodo">
        <fa-icon class="new-item-button-icon" icon="plus"/>
      </div>
    </div>

    <div class="task-list">

      <div
          :class="{task: true, done:item.done}"
          v-for="(item, index) in items"
          :key="index">

        <div class="task-check-box" :class="{checked: item.done}" @click="markTaskDone(index)">
          <fa-icon v-if="item.done" class="task-check-box-icon" icon="check"/>
        </div>

        <div class="task-item" @click="editingIndex = index" >
          <input
              @blur="updateTask(index, $event.target.value)"
              class="task-item-input"
              :class="{disabled: editingIndex !== index}"
              :value="item.item"/>
        </div>

        <div class="task-trash-button" @click="deleteTask(index)">
          <fa-icon class="task-trash-icon" icon="trash"/>
        </div>
      </div>
    </div>

    <div class="tasks-left-info">
      <h3>You have {{incompleteTaskCount}} pending tasks </h3>
      <button
          v-if="items.length > 0"
          class="tasks-left-clear-button"
          @click="clearTasks">
        Clear All
      </button>
    </div>

  </div>
</template>

<script>
export default {
  name: 'TodoList',
  props: {
    msg: String
  },
  data() {
    return {
      items: [
        { item: "Feed the dog", done: false },
        { item: "Do the shopping", done: false }
      ],
      newTodo: "",
      editingIndex: null
    }
  },
  computed: {

    incompleteTaskCount() {
      return this.items.filter(item => !item.done).length;
    }
  },
  methods: {
    addNewTodo() {
      const valid = this.validateNewTodo()

      if (valid) {
        this.items.unshift({item: this.newTodo, done: false});
        this.newTodo = "";
        this.moveIncompleteItemsUp();
      } else  {
        this.focusOnInput();
      }
    },
    validateNewTodo(optString) {
      if (optString) return optString.length >= 1;

      return this.newTodo.length >= 1;

    },
    focusOnInput() {
      this.$refs.newTask.focus();

    },
    deleteTask(index) {
      this.items.splice(index, 1)
    },

    clearTasks() {
      // TODO - Add in a notification for confirmation
      this.items = [];
    },

    markTaskDone(index) {
      this.items[index].done = !this.items[index].done

      this.moveIncompleteItemsUp();
    },

    updateTask(index, update) {
      const valid = this.validateNewTodo(update);

      if (valid)
        this.items[index].item = update;
      else
        //Sets the field back to what was stored
        this.$forceUpdate();
    },

    moveIncompleteItemsUp() {
      for(let i = 0; i < this.items.length; i++) {
        while (this.items[i].done && this.items[i+1] && !this.items[i+1].done) {
          this.swapItems(i, i+1)
        }
      }

    },

    swapItems(a, b) {
      let temp = this.items[a]
      this.items[a] = this.items[b]
      this.items[b] = temp
    }
  }

}
</script>

<style scoped>

.Todo-List {
  display: inline-block;
  border-radius: 10px;
  background-color: var(--color-background-panel);
  padding: 1rem 2rem;
  box-shadow: rgba(0, 0, 0, 0.05) 0 6px 24px 0, rgba(0, 0, 0, 0.08) 0 0 0 1px;
}

h1 {
  margin-bottom: 1rem;
  text-align: left;
}

.new-item {
  display: flex;
  align-items: stretch;
  width: 30rem;
  height: 3rem;
  margin: 0 auto;
}
.new-item-input {
  flex: 1;
  font-size: 18px;
  border-radius: var(--border-radius-default);
  padding: 0 8px;
  border: var(--border-default);
}

.new-item-input:focus {
  outline: none !important;
  border-radius: var(--border-radius-default);
  border: 1px solid var(--color-vue-green);
  padding: 0 8px;
}

.new-item-button {
  height: inherit;
  background: var(--color-vue-green);
  font-size: 20px;
  margin-left: .5rem;
  padding: 0 1rem;
  border-radius: 5px;
  cursor: pointer;
  opacity: 0.6;
  transition: 0.3s;
}

.new-item-button:hover {
  opacity: 1;
}

.new-item-button-icon {
  height: inherit;
  color: #F8F8F8;
}

.task-list {
  margin-top: 1rem;
}

.task {
  display: flex;
  height: 50px;
  margin-bottom: .5rem;
}

.task-item {
  flex: 1;
  margin-left: .5rem;
}

.task-check-box {
  align-self: center;
  height: 30px;
  width: 30px;
  border: 1px solid lightgrey;
  border-radius: var(--border-radius-default);
  background: #F8F8F8;
}

.task-check-box:hover {
  background: lightgrey;
  cursor: pointer;
}

.task-check-box.checked {
  background: var(--color-vue-green);
  opacity: 0.8;
  border: none;
  transition: 0.2s;
}

.task-check-box.checked:hover {
  opacity: 1;
}

.task-check-box-icon {
  height: inherit;
}

.task.done .task-item-input,  .task.done  .task-item-input:hover{
  background: #F8F8F8;
  cursor: default;
  text-decoration-line: line-through;
  color: darkgrey;
}

.task-item-input {
  height: 100%;
  width: 100%;
  border: none;
  background-color: #F8F8F8;
  border-radius: var(--border-radius-default);
  padding-left: 8px;
  cursor: text;
  color: var(--color-default);
}

.task-item-input:hover, .task-item-input.disabled:hover {
  background: lightgrey;
}


.task-item-input:focus, .task-item-input:focus:hover {
  outline: none;
  background: white;
}

.task-trash-button {
  height: inherit;
  font-size: 20px;
  margin-left: .5rem;
  padding: 0 1rem;
  background: none;
  border-radius: var(--border-radius-default);
  transition: 0.3s;
}

.task.done .task-trash-button {
  color: darkgrey;
}

.task-trash-button:hover, .task.done .task-trash-button:hover {
  background: var(--color-danger);
  color: white;
  cursor: pointer;
  border-radius: var(--border-radius-default);
}

.task-trash-icon {
  height: inherit;
}

.tasks-left-info {
  display: flex;
  justify-content: space-between;
  height: 2rem;
}

.tasks-left-info h3 {
  align-self: center;
}

.tasks-left-clear-button {
  background: darkgrey;
  border-radius: var(--border-radius-default);
  color: white;
  font-weight: bold;
  border: none;
  cursor: pointer;
  opacity: 0.6;
  transition: 0.3s;
}

.tasks-left-clear-button:hover {
  opacity: 1;
}

</style>
