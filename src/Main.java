private class RegisterListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String name = nameField.getText();
            String email = emailField.getText();

            if (name.isEmpty() || email.isEmpty()) {
                throw new IllegalArgumentException("Name and Email cannot be empty.");
            }

            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                throw new IllegalArgumentException("Please enter a valid Gmail address.");
            }

            if (enteredNames.contains(name)) {
                throw new IllegalArgumentException("This name has already been registered.");
            }

            enteredNames.add(name);
            isRegistered = true;  // Set the flag to true after successful registration

            // Disable name and email fields after registration without changing background color
            nameField.setEnabled(false);
            emailField.setEnabled(false);

            // Keep text color the same even when the fields are disabled
            nameField.setDisabledTextColor(Color.BLACK); // Text remains black
            emailField.setDisabledTextColor(Color.BLACK); // Text remains black

            JOptionPane.showMessageDialog(PersonalFinanceApp.this, "Registration successful!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(PersonalFinanceApp.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private class AddTransactionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        addTransaction("Standard Transaction");
    }
}

private class WithdrawListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        addTransaction("Withdraw");
    }
}

private class TransferListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        addTransaction("Transfer");
    }
}

private class LoanListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        addTransaction("Loan");
    }
}

private class ViewTransactionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();
        model.setRowCount(0);
        for (Transaction transaction : transactions) {
            model.addRow(new Object[]{transaction.getName(), transaction.getAmount(), transaction.getCategory(), transaction.getDate()});
        }
    }
}

private void addTransaction(String categoryOverride) {
    try {
        if (!isRegistered) {
            throw new IllegalArgumentException("Please register a name and email first.");
        }

        String name = nameField.getText();
        if (name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");

        double amount = Double.parseDouble(amountField.getText());
        if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than 0");

        String category = categoryOverride.equals("Standard Transaction") ? Objects.requireNonNull(categoryDropdown.getSelectedItem()).toString() : categoryOverride;
        String date = Objects.requireNonNull(dateDropdown.getSelectedItem()).toString();

        transactions.add(new Transaction(name, amount, category, date));
        JOptionPane.showMessageDialog(this, category + " added successfully!");
        clearInputFields();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
