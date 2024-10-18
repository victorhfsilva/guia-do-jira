import com.atlassian.jira.component.ComponentAccessor

def user = Users.getByName('hsilva_victor')

def issue = Issues.create('JAPREND', 'História') {
    setSummary('Criação de segunda Issue via Script Runner')
    setPriority('Medium')
    setComponents('Adm Jira 1')
    setReporter(user)
    setEpic('JAPREND-1')
    setSprints('JAPREND Sprint 1')
}

issue.transition('In Progress')