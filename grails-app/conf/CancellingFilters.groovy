
class CancellingFilters {
	def filters = {
		all(controller:"author", action:"create") {
			before = {
				redirect(controller:"author", action: 'list')
				return false
			}
		}
	}

}
