export const hasPermi = {
  mounted(el, binding) {
    const permissions = JSON.parse(localStorage.getItem('permissions') || '[]')
    const requiredPerm = binding.value
    
    if (!permissions.includes(requiredPerm)) {
      el.style.display = 'none'
    }
  }
}