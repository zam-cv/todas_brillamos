package integration

import (
	"backend/config"
	"backend/resources/mail"
	"testing"
)

// SendEmail envía un correo electrónico
func TestSendEmail(t *testing.T) {
	config.LoadEnvVars()

	payload := mail.EmailPayload{
		Title:  "Test Email",
		Body:   "This is a test email",
		Emails: []string{"m@example.com"},
	}

	err := mail.SendEmail(payload, config.ApiKeyMailer, config.EmailMailer)
	if err != nil {
		t.Errorf("Error sending email: %v", err)
	}

	if err != nil {
		return
	}
}
